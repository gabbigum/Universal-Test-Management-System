import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { TestRunDetailsComponent } from './test-run-details/test-run-details.component';

const routes: Routes = [{
  path: 'home',
  component: HomeComponent
}, {
  path: '',
  redirectTo: '/home',
  pathMatch: 'full'
},
{
  path: 'project/:projectId',
  component: ProjectDetailsComponent
}, {
  path: 'project/:projectId/runs/:runId',
  component: TestRunDetailsComponent
}];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectDetailsComponent,
    TestRunDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
