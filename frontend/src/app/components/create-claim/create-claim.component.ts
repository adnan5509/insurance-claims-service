import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClaimService } from '../../services/claim.service';
import { ClaimRequest } from '../../models/claim.model';

@Component({
  selector: 'app-create-claim',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <h2>Create New Claim</h2>
      <form (ngSubmit)="onSubmit()" #claimForm="ngForm">
        <div class="mb-3">
          <label for="description" class="form-label">Description</label>
          <textarea
            id="description"
            name="description"
            [(ngModel)]="claim.description"
            required
            class="form-control"
            rows="3"
          ></textarea>
        </div>
        <div class="mb-3">
          <label for="amount" class="form-label">Amount</label>
          <input
            type="number"
            id="amount"
            name="amount"
            [(ngModel)]="claim.amount"
            required
            class="form-control"
          >
        </div>
        <button type="submit" class="btn btn-primary" [disabled]="!claimForm.form.valid">
          Submit Claim
        </button>
      </form>
    </div>
  `,
  styles: [`
    .container {
      max-width: 800px;
      margin: 0 auto;
    }
  `]
})
export class CreateClaimComponent {
  claim: ClaimRequest = {
    description: '',
    amount: 0
  };
  customerId: number = 0;

  constructor(
    private claimService: ClaimService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.route.params.subscribe(params => {
      this.customerId = +params['customerId'];
    });
  }

  onSubmit() {
    this.claimService.createClaim(this.customerId, this.claim)
      .subscribe({
        next: () => {
          this.router.navigate(['/customers', this.customerId, 'claims']);
        },
        error: (error) => {
          console.error('Error creating claim:', error);
          alert('Failed to create claim. Please try again.');
        }
      });
  }
} 