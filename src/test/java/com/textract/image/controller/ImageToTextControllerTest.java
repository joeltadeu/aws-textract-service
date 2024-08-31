package com.textract.image.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.textract.image.model.ImageToTextResponse;
import com.textract.image.model.TextBlock;
import com.textract.image.model.TextGeometry;
import com.textract.image.service.ImageToTextService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

class ImageToTextControllerTest {

  @Mock private ImageToTextService service;

  @InjectMocks private ImageToTextController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testTextExtract_Success() {
    Resource mockResource = mock(Resource.class);
    var geometry = new TextGeometry(0.5f, 0.2f, 0.1f, 0.05f);
    ImageToTextResponse mockResponse =
        new ImageToTextResponse(List.of(new TextBlock(geometry, "Sample text")));
    when(service.extract(mockResource)).thenReturn(mockResponse);

    ResponseEntity<ImageToTextResponse> responseEntity = controller.textExtract(mockResource);

    assertEquals(ResponseEntity.ok(mockResponse), responseEntity);
    verify(service, times(1)).extract(mockResource);
  }

  @Test
  void testTextExtract_ServiceThrowsException() {
    Resource mockResource = mock(Resource.class);
    when(service.extract(mockResource)).thenThrow(new RuntimeException("Service failure"));

    try {
      controller.textExtract(mockResource);
    } catch (RuntimeException ex) {
      assertEquals("Service failure", ex.getMessage());
    }
    verify(service, times(1)).extract(mockResource);
  }
}
