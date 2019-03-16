package com.example.ricardogarcia.movieapp.application.builder;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context=context;
    }

    @Provides
    @AppScope
    Context provideContext(){
        return context;
    }
}
