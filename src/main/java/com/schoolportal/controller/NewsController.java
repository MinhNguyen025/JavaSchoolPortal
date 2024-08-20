package com.schoolportal.controller;

import com.schoolportal.model.News;
import com.schoolportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/list")
    public String listNews(Model model) {
        List<News> newsList = newsRepository.findAll();
        model.addAttribute("newsList", newsList);
        return "news/list-news";
    }

    @GetMapping("/create")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "news/create-news";
    }

    @PostMapping("/save")
    public String saveNews(@ModelAttribute("news") News news) {
        newsRepository.save(news);
        return "redirect:/news/list";
    }

    @GetMapping("/edit/{id}")
    public String editNewsForm(@PathVariable Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("news", news);
        return "news/edit-news";
    }

    @PostMapping("/update")
    public String updateNews(@ModelAttribute("news") News news) {
        newsRepository.save(news);
        return "redirect:/news/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsRepository.deleteById(id);
        return "redirect:/news/list";
    }

    @GetMapping("/view/{id}")
    public String viewNews(@PathVariable Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("news", news);
        return "news/view-news";
    }
}
