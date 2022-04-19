package com.prt.rezaroomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prt.rezaroomdatabase.database.Database;
import com.prt.rezaroomdatabase.database.entity.Pet;
import com.prt.rezaroomdatabase.database.entity.User;
import com.prt.rezaroomdatabase.fac.PetFactory;
import com.prt.rezaroomdatabase.fac.UserFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Database database;
    private TextView textView;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Database.getInstance(getBaseContext());
        compositeDisposable = new CompositeDisposable();

        findViewById(R.id.show_users_button).setOnClickListener(this);
        findViewById(R.id.show_pets_button).setOnClickListener(this);
        findViewById(R.id.insert_random_user).setOnClickListener(this);
        findViewById(R.id.insert_random_pet).setOnClickListener(this);
        textView = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_users_button) {
            showUsers();
        } else if (v.getId() == R.id.show_pets_button) {
            showPets();
        } else if (v.getId() == R.id.insert_random_user) {
            insertRandomUser();
        } else if (v.getId() == R.id.insert_random_pet) {
            insertRandomPet();
        }
    }

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
        Disposable disposable = database.petDao().getPets().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Pet>>() {
                    @Override
                    public void accept(List<Pet> pets) throws Throwable {
                        StringBuilder builder = new StringBuilder();
                        for (Pet pet : pets) {
                            builder.append(pet.getPetName()).append(" -- ");
                        }
                        textView.setText(pets.size() + " : " + builder.toString());
                    }
                });

        compositeDisposable.add(disposable);
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

    @Override
    protected void onPause() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onPause();
    }
}