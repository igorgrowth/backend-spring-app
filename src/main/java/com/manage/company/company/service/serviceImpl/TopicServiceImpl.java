package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.exeption.EntityAlreadyExistException;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.TopicMapper;
import com.manage.company.company.model.Topic;
import com.manage.company.company.repository.TopicRepo;
import com.manage.company.company.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepo topicRepo;

    public TopicServiceImpl(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        Topic existingTopic = topicRepo.findByTitle(topicDTO.getTitle());
        if (existingTopic != null) {
            throw new EntityAlreadyExistException("Topic", topicDTO.getTitle() + " already exists.");
        }

        Topic topic = TopicMapper.toEntity(topicDTO);
        Topic savedTopic = topicRepo.save(topic);
        log.info("Saved Topic with ID: {}", savedTopic.getId());
        return TopicMapper.toDTO(savedTopic);
    }

    @Override
    public List<TopicDTO> findAll() {
        List<Topic> topics = topicRepo.findAll();
        log.info("Found {} topics", topics.size());
        return topics.stream()
                .map(TopicMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TopicDTO getById(Long id) {
        Topic topic = topicRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + id));
        log.info("Found Topic with ID: {}", id);
        return TopicMapper.toDTO(topic);
    }

    @Override
    public TopicDTO getByTitle(String title) {
        Topic topic = topicRepo.findByTitle(title);
        if (topic == null) {
            throw new ResourceNotFoundException("Topic", "Title: " + title);
        }
        log.info("Found Topic with Title: {}", title);
        return TopicMapper.toDTO(topic);
    }

    @Override
    public TopicDTO delete(Long id) {
        Topic topic = topicRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + id));
        topicRepo.delete(topic);
        log.info("Deleted Topic with ID: {}", id);
        return TopicMapper.toDTO(topic);
    }

    @Override
    public TopicDTO update(TopicDTO topicDTO) {
        Topic existingTopic = topicRepo.findById(topicDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + topicDTO.getId()));
        existingTopic.setTitle(topicDTO.getTitle());
        existingTopic.setDescription(topicDTO.getDescription());
        Topic updatedTopic = topicRepo.save(existingTopic);
        log.info("Updated Topic with ID: {}", topicDTO.getId());
        return TopicMapper.toDTO(updatedTopic);
    }
}