package by.brest.karas.service.rest_app;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Version rest controller
 */
@RestController
public class VersionController {
    private final static String VERSION = "1.0";

//    public VersionController(String version){
//        this.VERSION = version;
//    };
    @GetMapping("/version")
    public String returnVersion(){
        return VERSION;
    }
}
