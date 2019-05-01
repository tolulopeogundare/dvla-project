package com.dvla.project.FilesService;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FileConfig {

    private List<String> VRM;
    private List<String> Colour;
}
