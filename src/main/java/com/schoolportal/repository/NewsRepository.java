package com.schoolportal.repository;

import com.schoolportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword%")
    List<News> searchByKeyword(String keyword);
}
