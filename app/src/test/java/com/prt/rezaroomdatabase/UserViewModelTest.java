package com.prt.rezaroomdatabase;

import com.prt.rezaroomdatabase.model.UserRepository;
import com.prt.rezaroomdatabase.model.database.entity.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(JUnit4.class)
public class UserViewModelTest {

    private UserRepository repository;

    @Before
    public void setup() {
        repository = mock(UserRepository.class);
    }

    @Test
    public void insertUser() {
        User user = getRandomUser();
        Completable completable = Completable.complete();

        insertUserMock(user, completable);

        repository.insertUser(user).test().assertComplete();
    }

    @Test
    public void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(getRandomUser());
        users.add(getRandomUser());

        Flowable<List<User>> flowable = Flowable.create(new FlowableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<User>> emitter) throws Throwable {
                emitter.onNext(users);
            }
        }, BackpressureStrategy.LATEST);

        getUsersMock(flowable);

        repository.getUsers().test().assertValue(users);
    }

    @Test
    public void test() {
        int a = 3;
        int b = 4;

        assertThat(a).isEqualTo(b);
    }


    public void getUsersMock(Flowable<List<User>> flowable) {
        when(repository.getUsers()).thenReturn(flowable);
    }

    public void insertUserMock(User user, Completable completable) {
        when(repository.insertUser(user)).thenReturn(completable);
    }

    public User getRandomUser() {
        return new User(1, "Reza", "09321587965", 12, "reza@gmail.com");
    }

}
