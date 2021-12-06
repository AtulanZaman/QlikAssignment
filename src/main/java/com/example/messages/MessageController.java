package com.example.messages;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageRepository repository;
    private final MessageModelAssembler assembler;

    MessageController(MessageRepository repository, MessageModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/messages")
    CollectionModel<EntityModel<Message>> all() {
        List<EntityModel<Message>> messages = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(messages,
                linkTo(methodOn(MessageController.class).all()).withSelfRel()
            );
    }

    @PostMapping("/messages")
    ResponseEntity<?> newMessage(@RequestBody Message newMessage){
        EntityModel<Message> entityModel = assembler.toModel(repository.save(newMessage));
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/messages/{id}")
    EntityModel<Message> one(@PathVariable Long id){
        Message message = repository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
        return this.assembler.toModel(message);
    }

    @PutMapping("/messages/{id}")
    ResponseEntity<?> replaceMessage(@RequestBody Message newMessage, @PathVariable Long id){
        Message updatedMessage = repository.findById(id).map(message -> {
                message.setText(newMessage.getText());
                return repository.save(message);
            }
        ).orElseGet(() -> {
            newMessage.setId(id);
            return repository.save(newMessage);
        });

        EntityModel<Message> entityModel = assembler.toModel(updatedMessage);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/messages/{id}")
    ResponseEntity<?> delteMessage(@PathVariable Long id){
        repository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }

}
