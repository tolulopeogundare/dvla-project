package com.dvla.project.Files_Service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FileConfig {

    private List<String> VRM;

    private List<String> Colour;
}
