import { Routes } from '@angular/router';
import { ClaimListComponent } from './components/claim-list/claim-list.component';
import { CreateClaimComponent } from './components/create-claim/create-claim.component';

export const routes: Routes = [
  { path: '', redirectTo: '/customers/1/claims', pathMatch: 'full' },
  { path: 'customers/:customerId/claims', component: ClaimListComponent },
  { path: 'customers/:customerId/claims/new', component: CreateClaimComponent }
];
