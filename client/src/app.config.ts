import { HTTP_INTERCEPTORS, provideHttpClient, withFetch } from '@angular/common/http';
import { TokenInterceptor } from './app/core/interceptors/token.interceptor';
import { ApplicationConfig, isDevMode } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { authReducer } from './app/core/store/auth/auth.reducer';
import { AuthEffects } from './app/core/store/auth/auth.effects';

export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(appRoutes, withInMemoryScrolling({ anchorScrolling: 'enabled', scrollPositionRestoration: 'enabled' }), withEnabledBlockingInitialNavigation()),
        provideHttpClient(withFetch()),
        { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
        provideAnimationsAsync(),
        providePrimeNG({ theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } } }),
        provideStore({ auth: authReducer }),
        provideEffects([AuthEffects]),        provideStoreDevtools({ maxAge: 25, logOnly: !isDevMode() }),
    ]
};
