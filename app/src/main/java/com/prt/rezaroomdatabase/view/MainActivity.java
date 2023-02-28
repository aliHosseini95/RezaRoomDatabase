package com.prt.rezaroomdatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.prt.rezaroomdatabase.R;
import com.prt.rezaroomdatabase.model.database.Database;
import com.prt.rezaroomdatabase.model.database.entity.Pet;
import com.prt.rezaroomdatabase.model.database.entity.User;
import com.prt.rezaroomdatabase.fac.PetFactory;
import com.prt.rezaroomdatabase.fac.UserFactory;
import com.prt.rezaroomdatabase.viewmodel.PetViewModel;
import com.prt.rezaroomdatabase.viewmodel.UserViewModel;

import org.reactivestreams.Subscription;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Database database;
    private TextView textView;

    private CompositeDisposable compositeDisposable;

    private UserViewModel userViewModel;
    @Inject
    public PetViewModel petViewModel;

    //    Text inputs
    private TextInputEditText petNameInputText, petTypeInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        database = Database.getInstance(getBaseContext());
        compositeDisposable = new CompositeDisposable();

        findViewById(R.id.show_users_button).setOnClickListener(this);
        findViewById(R.id.show_pets_button).setOnClickListener(this);
        findViewById(R.id.insert_random_user).setOnClickListener(this);
        findViewById(R.id.insert_random_pet).setOnClickListener(this);
        findViewById(R.id.insert_custom_pet_button).setOnClickListener(this);
        textView = findViewById(R.id.textView);

//        Text inputs
        petNameInputText = findViewById(R.id.pet_name_input_text);
        petTypeInputText = findViewById(R.id.pet_type_input_text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_users_button) {
//            showUsers();
            showUsersViewModel();
        } else if (v.getId() == R.id.insert_random_user) {
//            insertRandomUser();
            insertRandomUserViewModel();
        } else if (v.getId() == R.id.show_pets_button) {
            showPets();
        } else if (v.getId() == R.id.insert_random_pet) {
            insertRandomPet();
        } else if (v.getId() == R.id.insert_custom_pet_button) {
            insertCustomPet();
        }
    }

    //    this method insert a pet to the database
    private void insertCustomPet() {
        String name = String.valueOf(petNameInputText.getText());
        String type = String.valueOf(petTypeInputText.getText());
        Date lastVaccinatedDate = new Date(System.currentTimeMillis());
        Pet pet = new Pet(name, type, lastVaccinatedDate);
        compositeDisposable.add(
                petViewModel.insertPet(pet).subscribeOn(Schedulers.io()).subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("TEST_TAG", "insertPet Completed");
                    }
                })
        );
    }

    //<editor-fold desc="ViewModel Method">
    private void insertRandomUserViewModel() {
        Disposable disposable = userViewModel.insertUser(UserFactory.makeUser())
                .subscribeOn(Schedulers.io()).subscribe();
        compositeDisposable.add(disposable);
    }

    private void showUsersViewModel() {
        Disposable disposable = userViewModel.getUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Throwable {
                StringBuilder builder = new StringBuilder();
                for (User user : users) {
                    builder.append(user.getUserName()).append(" -- ");
                }
                textView.setText(users.size() + " : " + builder.toString());
            }
        });
        compositeDisposable.add(disposable);
    }
    //</editor-fold>

    //<editor-fold desc="Old Method">
    private void showUsers() {
        Disposable disposable = database.userDao().getUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) {
                        StringBuilder builder = new StringBuilder();
                        for (User user : users) {
                            builder.append(user.getUserName()).append(" -- ");
                        }
                        textView.setText(users.size() + " : " + builder.toString());
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void showPets() {
        compositeDisposable.add(
                petViewModel.getPets().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Pet>>() {
                    @Override
                    public void accept(List<Pet> pets) throws Throwable {
                        StringBuilder builder = new StringBuilder("");
                        for (Pet pet : pets) {
                            builder.append(pet.getPetId())
                                    .append(" : ")
                                    .append(pet.getPetName())
                                    .append(" : ")
                                    .append(pet.getType())
                                    .append(" : ")
                                    .append(pet.getLastVaccinatedDate().getYear())
                                    .append("\n");
                        }
                        textView.setText(builder.toString());
                    }
                })
        );
    }

    private void insertRandomUser() {
        Disposable disposable = Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                database.userDao().insertUser(UserFactory.makeUser());
            }
        }).subscribeOn(Schedulers.io()).subscribe();

        compositeDisposable.add(disposable);
    }

    private void insertRandomPet() {
        Disposable disposable = Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                database.petDao().insertPet(PetFactory.makePet());
            }
        }).subscribeOn(Schedulers.io()).subscribe();

        compositeDisposable.add(disposable);
    }
    //</editor-fold>

    @Override
    protected void onPause() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onPause();
    }
}