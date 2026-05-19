package app.dao;


import app.model.Showroom;
import app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ShowroomDAO {

    @Inject
    private GenericDao genericDao;

    public void insert(Showroom showroom) {

        genericDao.insert(showroom);
    }

    public void update(Showroom showroom) {

        genericDao.update(showroom);
    }

    public void delete(Long id) {

        genericDao.delete(Showroom.class, id);

    }

    public Showroom findByShowroomId(Long id) {

        return genericDao.selectById(Showroom.class, id);
    }

    public User findByUserId(Long id) {

        return genericDao.selectById(User.class, id);
    }

    public List<Showroom> findAll() {

        return genericDao.selectAll(Showroom.class);
    }
}