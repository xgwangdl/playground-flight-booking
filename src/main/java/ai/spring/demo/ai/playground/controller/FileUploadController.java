package ai.spring.demo.ai.playground.controller;

import ai.spring.demo.ai.playground.javaAssistant.JavaAssistant;
import lombok.SneakyThrows;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("fileupload")
public class FileUploadController {

    private final VectorStore vectorStore;

    @Autowired
    private JavaAssistant javaAssistant;

    public FileUploadController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @SneakyThrows
    @PostMapping("/java/embedding")
    public String uploadJavaDoc(@RequestParam MultipartFile file) {
        // 从IO流中读取文件
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new InputStreamResource(file.getInputStream()));
        // 将文本内容划分成更小的块
        List<Document> splitDocuments = new TokenTextSplitter()
                .apply(tikaDocumentReader.read());
        vectorStore.write(splitDocuments);
        return "ok";
    }

    /*@GetMapping("/questions")
    public String javaInterView(@RequestParam String questions) {
        return  this.javaAssistant.chat(questions);
    }*/
}
