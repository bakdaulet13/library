package kz.bakdaulet.library.service.impl;

import kz.bakdaulet.library.model.Publisher;
import kz.bakdaulet.library.repository.PublisherRepository;
import kz.bakdaulet.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void createPublisher(Publisher publisher) {
        Publisher newPublisher = new Publisher.Builder()
                .name(publisher.getName())
                .description(publisher.getDescription())
                .build();
        publisherRepository.save(newPublisher);
    }

    @Override
    public void updatePublisher(Publisher publisher) {

    }

    @Override
    @Transactional
    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
