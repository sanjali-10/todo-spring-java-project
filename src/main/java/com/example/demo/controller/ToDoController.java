package com.example.demo.controller;


import com.example.demo.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class ToDoController {

   private static List<Todo>todos;
    public ToDoController(){
        todos = new ArrayList<>();
        todos.add(new Todo(1,false,"Todo1",1));
        todos.add(new Todo(2,false,"Todo2",2));
        todos.add(new Todo(3,true,"Todo3",3));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(todos);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
        todos.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }
    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable long todoId){
        for(Todo todo :todos){
            if(todo.getId()==todoId){
                return ResponseEntity.status(HttpStatus.OK).body(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todo request of id " + todoId + " not Found");
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable long todoId){
       for(Todo todo: todos){
           if(todoId == todo.getId()){
               todos.remove(todo);
               return ResponseEntity.status(HttpStatus.OK).body(todo);
           }
       }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todo request of id " + todoId + " not Found");
    }

    @PutMapping
    public ResponseEntity<?> updateTodoObject(@RequestBody Todo updateTodo){
        for(int i = 0;i<todos.size();i++){
            if(todos.get(i).getId()==updateTodo.getId()){
                todos.set(i,updateTodo);
                return ResponseEntity.status(HttpStatus.OK).body("Todo Updated successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not found");
    }

    @PatchMapping("/todoByCompleted")
    public ResponseEntity<?> updateCompletedField(@RequestParam Boolean completed, @RequestParam Long id){
        for(int i = 0;i<todos.size();i++){
            if(todos.get(i).getId()==id){
                todos.get(i).setCompleted(completed);
                return ResponseEntity.status(HttpStatus.OK).body("Todo Updated successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not found");
    }

}
