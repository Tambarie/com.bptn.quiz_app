package com_bptn_quiz_app.interfaces;

import com_bptn_quiz_app.models.Questions;

@FunctionalInterface
public interface JsonDeserializer {
    Questions deserializes();
}


