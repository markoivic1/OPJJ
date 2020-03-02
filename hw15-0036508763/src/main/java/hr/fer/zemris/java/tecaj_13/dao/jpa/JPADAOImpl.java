package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
        return blogEntry;
    }

    @Override
    public BlogUser getBlogUser(String nick) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogUser.class, nick);
    }

    @Override
    public List<BlogUser> getUsers() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("select b from BlogUser as b", BlogUser.class)
                .getResultList();
    }

    @Override
    public List<BlogEntry> getEntries(String nick) throws DAOException {
        BlogUser blogUser;
        try {
            blogUser = JPAEMProvider.getEntityManager().createQuery("select b from BlogUser as b where b.nick=:nick", BlogUser.class)
                    .setParameter("nick", nick).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return JPAEMProvider.getEntityManager().createQuery("select b from BlogEntry as b where b.creator=:bu", BlogEntry.class)
                .setParameter("bu", blogUser)
                .getResultList();
    }

    @Override
    public void setEntry(BlogEntry blogEntry) throws DAOException {
        Long id = blogEntry.getId();
        BlogEntry blog = new BlogEntry();
        if (id != null) {
            blog = DAOProvider.getDAO().getBlogEntry(id);
        }
        blog.setLastModifiedAt(new Date());
        blog.setTitle(blogEntry.getTitle());
        blog.setText(blogEntry.getText());
        blog.setCreator(blogEntry.getCreator());
        if (id == null) {
            blog.setCreatedAt(new Date());
            JPAEMProvider.getEntityManager().persist(blog);
        }
    }

    @Override
    public void setComment(BlogComment blogComment) throws DAOException {
        JPAEMProvider.getEntityManager().persist(blogComment);
        JPAEMProvider.close();
    }

    @Override
    public void setBlogUser(BlogUser blogUser) throws DAOException {
        try {
            JPAEMProvider.getEntityManager().persist(blogUser);
            JPAEMProvider.close();
        } catch (Exception e) {
            throw new DAOException("Blog user with this nick already exists");
        }
    }
}
