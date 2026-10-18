import { useRef, useState } from 'react'

// The Web Speech API isn't in TypeScript's built-in types yet, so describe just what we use.
interface SpeechResultEvent {
  results: ArrayLike<ArrayLike<{ transcript: string }>>
}
interface SpeechErrorEvent {
  error: string
}
interface Recognition {
  lang: string
  interimResults: boolean
  onresult: ((e: SpeechResultEvent) => void) | null
  onerror: ((e: SpeechErrorEvent) => void) | null
  onend: (() => void) | null
  start(): void
  stop(): void
}
type RecognitionCtor = new () => Recognition

const w = window as unknown as {
  SpeechRecognition?: RecognitionCtor
  webkitSpeechRecognition?: RecognitionCtor
}
const RecognitionImpl = w.SpeechRecognition ?? w.webkitSpeechRecognition

/**
 * Wraps browser speech recognition. Always check `supported` and keep typing available:
 * Firefox has no speech API, and users can block the microphone.
 */
export function useSpeech(onText: (text: string) => void) {
  const supported = Boolean(RecognitionImpl)
  const [listening, setListening] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const current = useRef<Recognition | null>(null)

  function start() {
    if (!RecognitionImpl || listening) return
    const r = new RecognitionImpl()
    r.lang = 'en-IE' // try 'en-GB' if accuracy is poor
    r.interimResults = false
    r.onresult = (e) => onText(e.results[0][0].transcript)
    r.onerror = (e) =>
      setError(
        e.error === 'not-allowed'
          ? 'Microphone blocked. You can type the job instead.'
          : `Speech error: ${e.error}`,
      )
    r.onend = () => setListening(false)
    current.current = r
    setError(null)
    setListening(true)
    r.start()
  }

  function stop() {
    current.current?.stop()
  }

  return { supported, listening, error, start, stop }
}
