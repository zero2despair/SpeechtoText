package speechtotext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText_main {
	public static void main(String[] args) {
	    SpeechToText service = new SpeechToText();
	    service.setUsernameAndPassword("j16011", "j16011");

	    File audio = new File("./audio/output.wav");
	    RecognizeOptions options=null;
		try {
			options = new RecognizeOptions.Builder()
				.model("ja-JP_BroadbandModel")
			    .audio(audio)
			    .contentType(RecognizeOptions.ContentType.AUDIO_WAV)
			    .build();
		} catch (FileNotFoundException e) {
			SpeechRecognitionResults transcript = service.recognize(options).execute();

		    System.out.println(transcript);

		    JsonNode node = null;
		    ObjectMapper mapper = new ObjectMapper();
		    try{
		    	node = mapper.readTree(transcript.toString());
		    }catch(IOException e1){
		    	e1.printStackTrace();
		    }

		    String Transcript = node.get("results").get(0).get("alternatives").get(0).get("transcript").asText();
		    double Confidence = node.get("results").get(0).get("alternatives").get(0).get("confidence").asDouble();
		    System.out.println("transcript:"+Transcript);
			System.out.println("confidence:"+Confidence);
		  }
	}
}


