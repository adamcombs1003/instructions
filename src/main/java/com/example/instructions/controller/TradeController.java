package com.example.instructions.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.service.TradeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Trade Instructions", description = "Upload csv or json with trade instructions.")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Operation(
        summary = "Upload trade instructions file.", 
        description = "Upload csv or json with trade instructions."
    )
    @PostMapping(
        value = "/upload-file", 
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CanonicalTrade>> uploadFile(@RequestParam MultipartFile file) {
        if (file == null || file.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<CanonicalTrade> addedTrades = tradeService.processUploadedFile(file);
            return new ResponseEntity<>(addedTrades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
