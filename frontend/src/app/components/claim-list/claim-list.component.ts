import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ClaimService } from '../../services/claim.service';
import { InsuranceClaim } from '../../models/claim.model';

@Component({
  selector: 'app-claim-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <h2>Insurance Claims</h2>
      <div *ngIf="error" class="alert alert-danger">
        {{ error }}
      </div>
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Claim Number</th>
              <th>Description</th>
              <th>Amount</th>
              <th>Status</th>
              <th>Created At</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let claim of claims">
              <td>{{claim.claimNumber}}</td>
              <td>{{claim.description}}</td>
              <td>{{claim.amount | currency}}</td>
              <td>{{claim.status}}</td>
              <td>{{claim.createdAt | date}}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" (click)="updateStatus(claim)">Update Status</button>
                <button class="btn btn-sm btn-danger" (click)="deleteClaim(claim.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: [`
    .container {
      max-width: 1200px;
      margin: 0 auto;
    }
  `]
})
export class ClaimListComponent implements OnInit {
  claims: InsuranceClaim[] = [];
  customerId: number = 0;
  error: string = '';

  constructor(
    private claimService: ClaimService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.customerId = +params['customerId'];
      this.loadClaims();
    });
  }

  loadClaims() {
    this.error = '';
    this.claimService.getClaimsForCustomer(this.customerId)
      .subscribe({
        next: (claims) => {
          this.claims = claims;
        },
        error: (err) => {
          console.error('Error loading claims:', err);
          this.error = 'Failed to load claims. Please try again later.';
        }
      });
  }

  updateStatus(claim: InsuranceClaim) {
    // TODO: Implement status update modal/form
  }

  deleteClaim(claimId: number) {
    if (confirm('Are you sure you want to delete this claim?')) {
      this.claimService.deleteClaim(claimId)
        .subscribe({
          next: () => {
            this.loadClaims();
          },
          error: (err) => {
            console.error('Error deleting claim:', err);
            this.error = 'Failed to delete claim. Please try again later.';
          }
        });
    }
  }
} 