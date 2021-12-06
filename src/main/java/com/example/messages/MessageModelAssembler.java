package com.example.messages;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MessageModelAssembler implements RepresentationModelAssembler<Message, EntityModel<Message>>{

    @Override
    public EntityModel<Message> toModel(Message message) {
        return EntityModel.of(message,
            linkTo(methodOn(MessageController.class).one(message.getId())).withSelfRel(),
            linkTo(methodOn(MessageController.class).all()).withRel("messages")
        );
    }
    
}
