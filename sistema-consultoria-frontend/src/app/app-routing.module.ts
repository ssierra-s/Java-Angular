import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AppLayoutComponent } from "./layout/app.layout.component";

@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', redirectTo: 'home', pathMatch: 'full' },  // Redirige la ruta raíz a 'landing'
            {
                path: '', component: AppLayoutComponent,
                
            },
            { path: 'home', loadChildren: () => import('./demo/components/landing/landing.module').then(m => m.LandingModule) },
           
        ], { scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled', onSameUrlNavigation: 'reload' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
