package com.textract.image.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ImageToTextResponse(
    @Schema(description = "list of text extracted from image", name = "blocks") List<TextBlock> blocks) {}
