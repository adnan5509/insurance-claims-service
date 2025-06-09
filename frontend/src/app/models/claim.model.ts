export interface InsuranceClaim {
    id: number;
    customerId: number;
    claimNumber: string;
    description: string;
    status: string;
    amount: number;
    createdAt: Date;
    updatedAt: Date;
}

export interface ClaimRequest {
    description: string;
    amount: number;
}

export interface ClaimStatusUpdateRequest {
    status: string;
} 