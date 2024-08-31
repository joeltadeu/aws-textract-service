package com.textract.image.controller;

import com.textract.image.model.ImageToTextResponse;
import com.textract.image.service.ImageToTextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/images/image-to-text")
@Tag(
    name = "Image to Text Api",
    description = "This service is responsible to extract text from image")
public class ImageToTextController {

  private final ImageToTextService service;

  public ImageToTextController(ImageToTextService service) {
    this.service = service;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Extract text from image")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "text extracted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ImageToTextResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Error to upload image",
            content = @Content)
      })
  ResponseEntity<ImageToTextResponse> textExtract(
      @Valid
          @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
          @RequestPart(value = "file")
          Resource file) {
    var response = service.extract(file);
    return ResponseEntity.ok(response);
  }
}
