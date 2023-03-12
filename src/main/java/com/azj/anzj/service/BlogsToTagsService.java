package com.azj.anzj.service;

import java.util.List;

public interface BlogsToTagsService {

    public int deleteRel(Integer blogId);
    public int addRel(Integer blogId, List<Integer> tagIds);

}
