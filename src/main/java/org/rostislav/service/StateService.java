package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.State;
import org.rostislav.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public Collection<State> getAllStates() {
        return stateRepository.getAllStates();
    }

    public void addState(String stateName) {
        stateRepository.addState(new State(stateName));
    }

    public void updateState(int id, String stateName) {
        stateRepository.updateState(new State(stateName) {{
            setId(id);
        }});
    }

    public void deleteState(int id) {
        stateRepository.deleteState(id);
    }
}