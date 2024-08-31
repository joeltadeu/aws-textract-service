package com.textract.image.service;

import com.textract.image.model.ImageToTextResponse;
import com.textract.image.model.TextBlock;
import com.textract.image.model.TextGeometry;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.BlockType;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

@Service
public class ImageToTextService {

  private final TextractClient textractClient;

  public ImageToTextService(TextractClient textractClient) {
    this.textractClient = textractClient;
  }

  public ImageToTextResponse extract(Resource image) {
    try {
      byte[] imageBytes = image.getContentAsByteArray();
      DetectDocumentTextResponse response =
          textractClient.detectDocumentText(
              request ->
                  request
                      .document(
                          document -> document.bytes(SdkBytes.fromByteArray(imageBytes)).build())
                      .build());

      return transformTextDetectionResponse(response);
    } catch (IOException e) {
      throw new RuntimeException("Error to upload file");
    }
  }

  public ImageToTextResponse extract(String bucketName, String objectKey) {
    DetectDocumentTextResponse response =
        textractClient.detectDocumentText(
            request ->
                request
                    .document(
                        document ->
                            document
                                .s3Object(
                                    s3Object -> s3Object.bucket(bucketName).name(objectKey).build())
                                .build())
                    .build());

    return transformTextDetectionResponse(response);
  }

  private ImageToTextResponse transformTextDetectionResponse(DetectDocumentTextResponse response) {
    List<TextBlock> blocks =
        response.blocks().stream()
            .filter(block -> block.blockType().equals(BlockType.LINE))
            .map(
                block -> {
                  var geometry =
                      new TextGeometry(
                          block.geometry().boundingBox().width(),
                          block.geometry().boundingBox().height(),
                          block.geometry().boundingBox().top(),
                          block.geometry().boundingBox().left());
                  return new TextBlock(geometry, block.text());
                })
            .toList();
    return new ImageToTextResponse(blocks);
  }
}
