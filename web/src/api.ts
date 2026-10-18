// Types mirror the Java DTOs. Keep them in sync when you change the API.

export type TaxRate = 'ZERO' | 'STANDARD_DEMO'

export interface Customer {
  id: number
  name: string
  email: string | null
}

export interface ProposedLine {
  description: string
  quantity: number | null
  unitPrice: number | null
}

export type IssueType =
  | 'AMBIGUOUS_NUMBER'
  | 'AMOUNT_WITHOUT_CURRENCY'
  | 'NO_AMOUNT'
  | 'CUSTOMER_NOT_FOUND'
  | 'CUSTOMER_AMBIGUOUS'
  | 'TAX_NOT_CHOSEN'

export interface Issue {
  type: IssueType
  message: string
  fragment: string
}

export interface ParseResult {
  customerId: number | null
  lines: ProposedLine[]
  issues: Issue[]
}

/** Calls the API and throws an Error whose message is the server's "detail" text. */
async function request<T>(url: string, init?: RequestInit): Promise<T> {
  let res: Response
  try {
    res = await fetch(url, init)
  } catch {
    throw new Error('Server unreachable. Nothing was saved.')
  }
  if (!res.ok) {
    const body = await res.json().catch(() => null)
    throw new Error(body?.detail ?? `Request failed (${res.status})`)
  }
  return res.json() as Promise<T>
}

export const api = {
  customers: () => request<Customer[]>('/api/customers'),

  parse: (transcript: string) =>
    request<ParseResult>('/api/transcripts/parse', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ transcript }),
    }),

  // TODO (Isabelle): add createDraft(customerId, lines, idempotencyKey) that POSTs to
  // /api/invoices/drafts with an 'Idempotency-Key' header, and getInvoice(id).
}
