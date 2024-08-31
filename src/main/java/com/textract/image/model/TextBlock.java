package com.textract.image.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record TextBlock(
    @Schema(
            description = "Object contains the coordinates where the text was extracted",
            name = "geometry")
        TextGeometry geometry,
    @Schema(description = "text extracted from the image", name = "text", example = "Little Prince")
        String text) {}
