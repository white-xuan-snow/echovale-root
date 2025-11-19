package com.echovale.music.domain.repository;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

@Service
public interface AuthorRepository {
    Author findOrCreateByNeteaseId(Author author);
}
