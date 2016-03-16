package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsAcquisitionDao;
import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsAcquisitionDaoImpl extends
		HibernateBaseDao<CmsAcquisition, Integer> implements CmsAcquisitionDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<CmsAcquisition> getList(Integer siteId) {
		Finder f = Finder.create("from CmsAcquisition bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
	public CmsAcquisition findById(Integer id) {
		CmsAcquisition entity = get(id);
		return entity;
	}

	@Override
	public CmsAcquisition save(CmsAcquisition bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public CmsAcquisition deleteById(Integer id) {
		CmsAcquisition entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public int countByChannelId(Integer channelId) {
		String hql = "select count(*) from CmsAcquisition bean"
				+ " where bean.channel.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	public CmsAcquisition getStarted(Integer siteId) {
		Criteria crit = createCriteria(
				Restrictions.eq("status", CmsAcquisition.START),
				Restrictions.eq("site.id", siteId)).setMaxResults(1);
		return (CmsAcquisition) crit.uniqueResult();
	}

	@Override
	public Integer getMaxQueue(Integer siteId) {
		Query query = createQuery("select max(bean.queue) from CmsAcquisition bean where bean.site.id=?",siteId);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum) {
		Finder f = Finder.create("from CmsAcquisition bean where bean.queue>:queueNum and bean.site.id=:siteId")
				.setParam("queueNum", queueNum)
				.setParam("siteId", siteId);
		return find(f);
	}

	@Override
	public CmsAcquisition popAcquFromQueue(Integer siteId) {
		Query query = getSession().createQuery("from CmsAcquisition bean where bean.queue>0 and bean.site.id=:siteId order by bean.queue")
				.setParameter("siteId", siteId).setMaxResults(1);
		return (CmsAcquisition) query.uniqueResult();
	}

	@Override
	protected Class<CmsAcquisition> getEntityClass() {
		return CmsAcquisition.class;
	}

}