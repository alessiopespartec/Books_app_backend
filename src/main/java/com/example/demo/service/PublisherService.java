package com.example.demo.service;

import com.example.demo.entity.Publisher;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisher(Long id) {
        return findPublisherById(id);
    }

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Publisher publisher, Long id) {
        Publisher publisherToUpdate = findPublisherById(id);

        publisherToUpdate.setName(publisher.getName());
        return publisherRepository.save(publisherToUpdate);
    }

    public void deletePublisher(Long id) {
        publisherRepository.delete(findPublisherById(id));
    }

    private Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher", id));
    }
}
