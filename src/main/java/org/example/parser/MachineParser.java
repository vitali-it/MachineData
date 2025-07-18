package org.example.parser;

import org.example.FluorescenceModel;

import java.io.File;
import java.util.List;

public interface MachineParser {

    List<FluorescenceModel> parse(File file) throws Exception;

}
