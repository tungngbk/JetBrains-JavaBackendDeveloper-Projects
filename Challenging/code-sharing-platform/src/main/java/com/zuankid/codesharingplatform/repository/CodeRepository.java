package com.zuankid.codesharingplatform.repository;

import com.zuankid.codesharingplatform.entity.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CodeRepository extends CrudRepository<Code, UUID> {
    List<Code> findTop10ByOrderByDateDesc();
    List<Code> findAllByOrderByDateAsc();
}
