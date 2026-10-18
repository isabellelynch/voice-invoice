import { useEffect, useState } from 'react'
import './App.css'
import { api, type Customer, type ParseResult } from './api'
import { useSpeech } from './useSpeech'

/**
 * Starting point: pick a customer, speak or type the job, and see what the parser proposes.
 *
 * TODO (Isabelle), in order:
 *  1. Turn the parse result into an editable draft editor: one row per line with description,
 *     quantity, unit price and a tax-rate dropdown that starts EMPTY (the user must choose).
 *  2. Show the issues next to the fields they relate to.
 *  3. Save with api.createDraft, using one crypto.randomUUID() idempotency key per draft
 *     and disabling the button while saving.
 *  4. Add a print view route that loads the saved draft from the API (not from form state)
 *     and shows "DRAFT — NOT SENT".
 */
export default function App() {
  const [customers, setCustomers] = useState<Customer[]>([])
  const [customerId, setCustomerId] = useState<number | ''>('')
  const [transcript, setTranscript] = useState('')
  const [result, setResult] = useState<ParseResult | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [busy, setBusy] = useState(false)

  const speech = useSpeech((text) => setTranscript((prev) => `${prev} ${text}`.trim()))

  useEffect(() => {
    api.customers().then(setCustomers).catch((e: Error) => setError(e.message))
  }, [])

  async function parse() {
    setBusy(true)
    setError(null)
    try {
      const r = await api.parse(transcript)
      setResult(r)
      if (r.customerId !== null) setCustomerId(r.customerId)
    } catch (e) {
      setError((e as Error).message)
    } finally {
      setBusy(false)
    }
  }

  return (
    <main className="page">
      <h1>Voice Invoice</h1>
      <p className="hint">Prototype. Fictional data only. Voice input only ever creates a draft.</p>

      <label>
        Customer
        <select
          value={customerId}
          onChange={(e) => setCustomerId(e.target.value === '' ? '' : Number(e.target.value))}
        >
          <option value="">Choose a customer…</option>
          {customers.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>
      </label>

      <label>
        Describe the job
        <textarea
          rows={4}
          value={transcript}
          onChange={(e) => setTranscript(e.target.value)}
          placeholder="Customer John Murphy, labour one hundred and twenty euro, materials eighty five euro fifty"
        />
      </label>

      <div className="actions">
        {speech.supported ? (
          <button type="button" onClick={speech.listening ? speech.stop : speech.start}>
            {speech.listening ? 'Stop listening' : '🎤 Speak'}
          </button>
        ) : (
          <span className="hint">Speech isn't supported in this browser, so please type instead.</span>
        )}
        <button type="button" onClick={parse} disabled={busy || transcript.trim() === ''}>
          {busy ? 'Reading…' : 'Read transcript'}
        </button>
      </div>

      {speech.error && <p className="error">{speech.error}</p>}
      {error && <p className="error">{error}</p>}

      {result && (
        <section>
          <h2>What I heard (check before saving)</h2>
          <pre>{JSON.stringify(result, null, 2)}</pre>
        </section>
      )}
    </main>
  )
}
