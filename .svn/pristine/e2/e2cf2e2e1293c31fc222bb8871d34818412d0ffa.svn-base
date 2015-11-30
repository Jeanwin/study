package com.zonekey.study.dao;

import java.util.List;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Note;

@MyBatisRepository
public interface NoteMapper extends BaseMapper<Note, String> {
	public List<Note> selectNoteByWhere(Note note);

	public int updateNote(Note note);

	public int addNote(Note note);
	
	public int deleteNote(String id);
}
