package sschr15.qol.api.events;

import javax.annotation.Nullable;

/**
 * A result for an event.
 */
public enum EventResult {
    FORCE_OK,
    OK,
    NO,
    FORCE_NO;

    /**
     * A utility to work with {@link EventResult}{@code s}. Create an instance to use.
     */
    public static class ResultCounter {

        private int okCount;
        private int noCount;
        private EventResult result;

        /**
         * Set the result of the event
         *
         * @param result the requested result
         * @return if the vote mattered
         */
        public boolean setResult(@Nullable EventResult result) {
            if (result == null) return false;
            switch (result) {
                case OK:
                    this.okCount++;
                case NO:
                    this.noCount++;
                case FORCE_NO:
                    this.result = this.result == null ? FORCE_NO : this.result;
                    return true;
                case FORCE_OK:
                    this.result = this.result != FORCE_NO ? result : FORCE_OK;
                    return this.result == result;
                default:
                    return this.result == null;
            }
        }

        /**
         * Get the result of the event. Only will return {@link #OK}, {@link #NO}, or {@code null}.
         * If it is {@link #FORCE_OK} or {@link #FORCE_NO}, it is converted first.
         * If it returns {@code null}, there was an even vote between OK and NO.
         */
        @Nullable
        public EventResult getResult() {
            if (this.result != null) return this.result == FORCE_OK ? OK : NO;
            if (this.okCount == this.noCount) return null;
            return this.okCount > this.noCount ? OK : NO;
        }

        /**
         * Force the result regardless of the current status.
         * @param result either {@link #FORCE_OK} or {@link #FORCE_NO}
         */
        public void forceSetResult(EventResult result) {
            if (result == OK || result == NO) return;
            this.result = result;
        }
    }
}
