package com.echovale.music.domain.repository;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorRepository {
    Author findOrCreateByNeteaseId(Author author);

    List<Author> save(List<Author> authors);
}
