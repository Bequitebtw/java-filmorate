package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundMpaException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaService {
    private final MpaRepository mpaRepository;


    public List<Mpa> getAllMpa() {
        return mpaRepository.findAllMpa();
    }

    public Mpa getMpaById(long mpaId) {
        return mpaRepository.findMpaById(mpaId).orElseThrow(() -> new NotFoundMpaException(mpaId));
    }
}
