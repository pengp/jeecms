package com.jeecms.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.main.ContentTypeDao;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.common.hibernate3.Updater;

@Service
@Transactional
public class ContentTypeMngImpl implements ContentTypeMng {
	@Override
	@Transactional(readOnly = true)
	public List<ContentType> getList(boolean containDisabled) {
		return dao.getList(containDisabled);
	}

	@Override
	@Transactional(readOnly = true)
	public ContentType getDef() {
		return dao.getDef();
	}

	@Override
	@Transactional(readOnly = true)
	public ContentType findById(Integer id) {
		ContentType entity = dao.findById(id);
		return entity;
	}

	@Override
	public ContentType save(ContentType bean) {
		dao.save(bean);
		return bean;
	}

	@Override
	public ContentType update(ContentType bean) {
		Updater<ContentType> updater = new Updater<ContentType>(bean);
		ContentType entity = dao.updateByUpdater(updater);
		return entity;
	}

	@Override
	public ContentType deleteById(Integer id) {
		ContentType bean = dao.deleteById(id);
		return bean;
	}

	@Override
	public ContentType[] deleteByIds(Integer[] ids) {
		ContentType[] beans = new ContentType[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private ContentTypeDao dao;

	@Autowired
	public void setDao(ContentTypeDao dao) {
		this.dao = dao;
	}
}