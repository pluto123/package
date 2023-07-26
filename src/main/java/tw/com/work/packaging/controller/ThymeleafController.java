package tw.com.work.packaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThymeleafController {
    @GetMapping("/model")
    public String model(Model model) {
        model.addAttribute("message", "Hello Model");
        return "model";
    }

    @GetMapping("/modelMap")
    public String modelMap(ModelMap modelMap) {
        modelMap.addAttribute("message", "Hello ModelMap");
        return "model";
    }

    @GetMapping("/modelAndView")
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView("model");
        modelAndView.addObject("message", "Hello ModelAndView");
        return modelAndView;
    }

    @GetMapping("/modelAttribute")
    public String modelAttribute(@ModelAttribute("myItem") Item item) {
        item.setName("steven");
        item.setAge(48);

        return "modelAttribute";
    }
}
