package com.textract.image.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.textract.image.model.ImageToTextResponse;
import com.textract.image.model.TextBlock;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;

public class ImageToTextServiceTest {
  @Mock private TextractClient textractClient;

  @InjectMocks private ImageToTextService imageToTextService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void extract_withImageResource_successfulExtraction() throws IOException {
    Resource imageResource = mock(Resource.class);
    byte[] imageBytes = new byte[] {1, 2, 3};
    when(imageResource.getContentAsByteArray()).thenReturn(imageBytes);

    DetectDocumentTextResponse textractResponse = createTextractResponse();
    when(textractClient.detectDocumentText(any(Consumer.class))).thenReturn(textractResponse);

    ImageToTextResponse result = imageToTextService.extract(imageResource);

    assertEquals(1, result.blocks().size());
    TextBlock block = result.blocks().get(0);
    assertEquals("Sample text", block.text());
    assertEquals(0.5F, block.geometry().width());
    assertEquals(0.2F, block.geometry().height());
    assertEquals(0.1F, block.geometry().top());
    assertEquals(0.05F, block.geometry().left());
  }

  @Test
  void extract_withImageResource_throwsIOException() throws IOException {
    Resource imageResource = mock(Resource.class);
    when(imageResource.getContentAsByteArray()).thenThrow(new IOException("IO Error"));

    assertThrows(RuntimeException.class, () -> imageToTextService.extract(imageResource));
  }

  @Test
  void extract_withS3Object_successfulExtraction() {
    String bucketName = "test-bucket";
    String objectKey = "test-key";

    DetectDocumentTextResponse textractResponse = createTextractResponse();
    when(textractClient.detectDocumentText(any(Consumer.class))).thenReturn(textractResponse);

    ImageToTextResponse result = imageToTextService.extract(bucketName, objectKey);

    assertEquals(1, result.blocks().size());
    TextBlock block = result.blocks().get(0);
    assertEquals("Sample text", block.text());
    assertEquals(0.5F, block.geometry().width());
    assertEquals(0.2F, block.geometry().height());
    assertEquals(0.1F, block.geometry().top());
    assertEquals(0.05F, block.geometry().left());
  }

  private DetectDocumentTextResponse createTextractResponse() {
    BoundingBox boundingBox =
        BoundingBox.builder().width(0.5f).height(0.2f).top(0.1f).left(0.05f).build();

    Geometry geometry = Geometry.builder().boundingBox(boundingBox).build();

    Block block =
        Block.builder().blockType(BlockType.LINE).text("Sample text").geometry(geometry).build();

    return DetectDocumentTextResponse.builder().blocks(List.of(block)).build();
  }
}
