package ExtractImagePDFData;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;

public class AmazonTextractOCR {
	
	public static void main(String[] args) throws Throwable {
		getText("C:\\Users\\user\\Downloads\\Screenshot 2024-08-22 161202.png");
	}
    public static String getText(String imagePath) throws Exception {
        AmazonTextract client = AmazonTextractClientBuilder.standard().build();

        byte[] bytes = Files.readAllBytes(new File(imagePath).toPath());
        ByteBuffer imageBytes = ByteBuffer.wrap(bytes);

        DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                .withDocument(new Document().withBytes(imageBytes));

        DetectDocumentTextResult result = client.detectDocumentText(request);

        StringBuilder text = new StringBuilder();
        for (Block block : result.getBlocks()) {
            if ("LINE".equals(block.getBlockType())) {
                text.append(block.getText()).append("\n");
            }
        }

        System.out.println("Extracted Text: \n" + text.toString());
        return text.toString();
    }
}
