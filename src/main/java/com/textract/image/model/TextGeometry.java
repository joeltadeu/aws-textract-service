package com.textract.image.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record TextGeometry(
    @Schema(description = "Width of the block contains the text", name = "width", example = "100")
        Float width,
    @Schema(description = "Height of the block contains the text", name = "height", example = "10")
        Float height,
    @Schema(
            description = "Top coordinate of the block contains the text",
            name = "top",
            example = "0.8")
        Float top,
    @Schema(
            description = "Left coordinate of the block contains the text",
            name = "left",
            example = "0.7")
        Float left) {}
