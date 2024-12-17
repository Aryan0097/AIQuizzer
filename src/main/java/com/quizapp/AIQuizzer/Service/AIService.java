package com.quizapp.AIQuizzer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.AIQuizzer.Entity.Quiz;
import com.quizapp.AIQuizzer.Repository.QuizRepository;
import com.quizapp.AIQuizzer.Request.QuizRequest;

import dev.langchain4j.model.openai.OpenAiChatModel;

@Service
public class AIService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final QuizRepository quizRepository;
    private final ObjectMapper objectMapper;

    private OpenAiChatModel chatModel;


    public AIService(QuizRepository quizRepository, ObjectMapper objectMapper) {
        this.quizRepository = quizRepository;
        this.objectMapper = objectMapper;
        initializeChatModel();
    }

    private void initializeChatModel() {
        try {
            this.chatModel = OpenAiChatModel.builder()
                .apiKey("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")
                .baseUrl("https://api.groq.com/openai/v1")
                .modelName("llama-3.1-70b-versatile")
                .temperature(0.4)
                .maxTokens(2048)
                .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize OpenAI Chat Model", e);
        }
    }

    public Quiz generateQuiz(QuizRequest quizRequest) {
        String message = String.format(
            "new task Generate %d MCQ type questions on %s for grade %s with small hints in 2-3 words in the given JSON format, no extra text:\n" +
            "{\n" +
            "  \"subject\": \"%s\",\n" +
            "  \"gradeLevel\":\"%s\",\n" +
            "  \"questions\": [\n" +
            "    {\n" +
            "      \"questionId\": 1,\n" +
            "      \"questionText\": \"What is the capital of France?\",\n" +
            "      \"answerOptions\": [\"Paris\", \"London\", \"Berlin\", \"Rome\"],\n" +
            "      \"correctAnswer\": \"Paris\",\n" +
            "      \"hint\": \"It's the city of the Eiffel Tower.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}", 
                    quizRequest.getNumberOfQuestions(), 
                    quizRequest.getSubject(), 
                    quizRequest.getGradeLevel(), 
                    quizRequest.getSubject(), 
                    quizRequest.getGradeLevel());

        String aiResponse = chatModel.generate(message);
        System.out.println(aiResponse);

        Quiz quiz = parseQuizResponse(aiResponse, quizRequest.getSubject(), quizRequest.getGradeLevel());

        return quizRepository.save(quiz);
    }


    private Quiz parseQuizResponse(String aiResponse, String subject, String gradeLevel) {
        try {
            Quiz quiz = objectMapper.readValue(aiResponse, Quiz.class);
            quiz.setSubject(subject);
            quiz.setGradeLevel(gradeLevel);

            if (quiz.getQuestions() != null) {
                quiz.getQuestions().forEach(question -> question.setQuiz(quiz));
            }
    
            return quiz;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
    
}
