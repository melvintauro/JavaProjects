package pctimer.com; 

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class AiJavaApplication {

    public static void main(String[] args) {
        // 1. Configure your OpenAI API key (Replace with your actual key or use System environment variables)
        String apiKey = System.getenv("OPENAI_API_KEY"); 
        
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Please set your OPENAI_API_KEY environment variable.");
            return;
        }

        // 2. Initialize the AI Chat Model (Using GPT-4o-mini as a cost-effective, fast choice)
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .temperature(0.7)
                .build();

        // 3. Define the prompt/input for the AI
        String prompt = "Explain the concept of 'Polymorphism' in Java using a short 2-sentence analogy.";

        System.out.println("Sending prompt to AI: \n\"" + prompt + "\"\n");

        // 4. Generate the response
        String response = model.generate(prompt);

        // 5. Output the result
        System.out.println("AI Response:");
        System.out.println(response);
    }
}
