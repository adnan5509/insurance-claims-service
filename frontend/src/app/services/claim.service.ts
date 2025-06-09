import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InsuranceClaim, ClaimRequest, ClaimStatusUpdateRequest } from '../models/claim.model';

@Injectable({
  providedIn: 'root'
})
export class ClaimService {
  private apiUrl = 'http://localhost:8080/api/claims';

  constructor(private http: HttpClient) { }

  createClaim(customerId: number, claim: ClaimRequest): Observable<InsuranceClaim> {
    return this.http.post<InsuranceClaim>(`${this.apiUrl}/customers/${customerId}/claims`, claim);
  }

  updateClaimStatus(claimId: number, statusUpdate: ClaimStatusUpdateRequest): Observable<InsuranceClaim> {
    return this.http.put<InsuranceClaim>(`${this.apiUrl}/claims/${claimId}/status`, statusUpdate);
  }

  deleteClaim(claimId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/claims/${claimId}`);
  }

  getClaimsForCustomer(customerId: number): Observable<InsuranceClaim[]> {
    return this.http.get<InsuranceClaim[]>(`${this.apiUrl}/customers/${customerId}/claims`);
  }
} 