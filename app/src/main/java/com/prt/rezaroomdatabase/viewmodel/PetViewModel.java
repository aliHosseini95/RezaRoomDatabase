package com.prt.rezaroomdatabase.viewmodel;

import com.prt.rezaroomdatabase.model.PetRepository;
import com.prt.rezaroomdatabase.model.database.entity.Pet;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class PetViewModel extends ViewModel {

    private final PetRepository repository;

    @Inject
    public PetViewModel(PetRepository repository) {
        this.repository = repository;
    }

    public Completable insertPet(Pet pet) {
        return repository.insertPet(pet);
    }

    public Flowable<List<Pet>> getPets() {
        return repository.getPets();
    }
}
